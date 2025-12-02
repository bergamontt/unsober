import ExcelJS from "exceljs";
import { saveAs } from 'file-saver';
import type { CourseClass } from '../models/CourseClass';
import { getScheduleTemplate } from './ScheduleTemplateService';
import type { Student } from '../models/Student';
import { Term } from '../models/Subject';
import type { AppState } from '../models/AppState';

const COL_SUBJECT = 3; // C
const COL_GROUP = 4;   // D
const COL_WEEKS = 5;   // E
const COL_LOCATION = 6;// F
const COL_TYPE = 7;    // G

const BASE_DAY_START_ROW: Record<string, number> = {
    MONDAY: 7,
    TUESDAY: 14,
    WEDNESDAY: 21,
    THURSDAY: 28,
    FRIDAY: 35,
    SATURDAY: 42,
    SUNDAY: 49
};

const CLASS_NAMES: Record<string, string> = {
    PRACTICE: 'Практика',
    SEMINAR: 'Семінар',
    LECTURE: 'Лекція',
    CONSULTATION: 'Консультація',
    CREDIT: 'Залік',
    EXAM: 'Екзамен'
};


function compactWeeks(weeks: number[]): string {
    if (!weeks || weeks.length === 0) return '';
    const sorted = Array.from(new Set(weeks)).sort((a, b) => a - b);
    const ranges: string[] = [];
    let start = sorted[0], prev = sorted[0];
    for (let i = 1; i < sorted.length; i++) {
        const cur = sorted[i];
        if (cur === prev + 1) {
            prev = cur;
            continue;
        } else {
            ranges.push(start === prev ? `${start}` : `${start}-${prev}`);
            start = cur;
            prev = cur;
        }
    }
    ranges.push(start === prev ? `${start}` : `${start}-${prev}`);
    return ranges.join(',');
}

function copyRowStyle(sheet: ExcelJS.Worksheet, sourceRowNumber: number, targetRowNumber: number) {
    const src = sheet.getRow(sourceRowNumber);
    sheet.insertRow(targetRowNumber, []);
    const dest = sheet.getRow(targetRowNumber);
    dest.height = src.height;
    src.eachCell({ includeEmpty: true }, (cell, colNumber) => {
        const dcell = dest.getCell(colNumber);
        dcell.style = Object.assign({}, cell.style || {});
        if (cell.numFmt)
            dcell.numFmt = cell.numFmt;
    });
    dest.hidden = src.hidden;
    dest.commit();
}

export async function generateSchedule(
    student: Student | null,
    state: AppState | null,
    classes: CourseClass[] | null
) {
    if (student == null || state == null || classes == null)
        return;
    const res = await getScheduleTemplate();
    const arrayBuffer = await res.arrayBuffer();

    const workbook = new ExcelJS.Workbook();
    await workbook.xlsx.load(arrayBuffer);
    const sheet = workbook.getWorksheet(1);

    if (!sheet)
        return;

    var semester;
    switch (state.term) {
        case Term.AUTUMN:
            semester = "осінній";
            break;
        case Term.SPRING:
            semester = "весняний";
            break;
        default:
            semester = "літній";
    }
    const year = state.currentYear;

    sheet.getCell("A3").value = `Спеціальність "${student.speciality.name}", ${student.studyYear} р.н.`;
    sheet.getCell("A4").value = `Розклад занять на ${semester} семестр ${year}-${year + 1} н.р.`;

    const slotMap = new Map<string, CourseClass[]>();
    for (const c of classes) {
        const key = `${c.weekDay}_${c.classNumber}`;
        const arr = slotMap.get(key) ?? [];
        arr.push(c);
        slotMap.set(key, arr);
    }

    const dayOrder = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
    let totalInsertedRows = 0;

    for (const day of dayOrder) {
        const originalStart = BASE_DAY_START_ROW[day];
        let dayStart = originalStart + totalInsertedRows;
        let perDayInserted = 0;

        for (let slot = 1; slot <= 7; slot++) {
            const key = `${day}_${slot}`;
            const classesInSlot = slotMap.get(key) ?? [];

            const targetRowForSlot = dayStart + (slot - 1) + perDayInserted;
            const slotStart = targetRowForSlot;

            if (classesInSlot.length > 0) {
                classesInSlot.sort((a, b) => {
                    const sa = a.title ?? a.group.course.subject?.name ?? "";
                    const sb = b.title ?? b.group.course.subject?.name ?? "";
                    if (sa !== sb) return sa.localeCompare(sb);
                    return (a.group?.groupNumber ?? 0) - (b.group?.groupNumber ?? 0);
                });
            }

            for (let idx = 0; idx < classesInSlot.length; idx++) {
                const cls = classesInSlot[idx];
                const rowToUse = targetRowForSlot + idx;
                if (idx > 0) {
                    copyRowStyle(sheet, targetRowForSlot, rowToUse);
                    perDayInserted += 1;
                    totalInsertedRows += 1;
                }
                const row = sheet.getRow(rowToUse);

                const subjectName = cls.title ?? cls.group.course.subject.name;

                let teacherStr = '';
                if (cls.teacher) {
                    const t = cls.teacher;
                    teacherStr = `${t.lastName || ''} ${t.firstName || ''}${t.patronymic ? ' ' + t.patronymic : ''}`.trim();
                }

                const subjectCellVal = teacherStr ? `${subjectName}\n(${teacherStr})` : subjectName;

                row.getCell(COL_SUBJECT).value = subjectCellVal;
                row.getCell(COL_GROUP).value = cls.group?.groupNumber ?? '';
                row.getCell(COL_WEEKS).value = compactWeeks(cls.weeksList ?? []);
                const locParts: string[] = [];
                if (cls.location)
                    locParts.push(cls.location);
                if (cls.building?.name)
                    locParts.push(cls.building.name);
                row.getCell(COL_LOCATION).value = locParts.join(', ');
                row.getCell(COL_TYPE).value = CLASS_NAMES[cls.type] ?? '';

                row.commit();
            }

            const rowsCount = classesInSlot.length > 0 ? classesInSlot.length : 1;
            const slotEnd = slotStart + rowsCount - 1;

            const topBAddr = `B${slotStart}`;
            const bottomBAddr = `B${slotEnd}`;
            const topBCell = sheet.getCell(topBAddr);
            const originalBValue = topBCell.value;
            const originalBStyle = Object.assign({}, topBCell.style || {});

            if (slotEnd >= slotStart) {
                sheet.mergeCells(`${topBAddr}:${bottomBAddr}`);
                const mergedB = sheet.getCell(topBAddr);
                mergedB.value = originalBValue;
                mergedB.style = Object.assign({}, originalBStyle);
                mergedB.alignment = Object.assign({}, mergedB.alignment || {}, {
                    vertical: 'middle',
                    horizontal: 'center',
                    wrapText: true
                });
            } else {
                topBCell.alignment = Object.assign({}, topBCell.alignment || {}, {
                    vertical: 'middle',
                    horizontal: 'center',
                    wrapText: true
                });
            }
        }

        const dayEnd = dayStart + 7 - 1 + perDayInserted;

        if (dayEnd > dayStart) {
            const topCellAddr = `A${dayStart}`;
            const bottomCellAddr = `A${dayEnd}`;
            const topCell = sheet.getCell(topCellAddr);
            const originalValue = topCell.value;
            const originalStyle = Object.assign({}, topCell.style || {});

            sheet.mergeCells(`${topCellAddr}:${bottomCellAddr}`);

            const mergedCell = sheet.getCell(topCellAddr);
            mergedCell.value = originalValue;
            mergedCell.style = Object.assign({}, originalStyle);
            mergedCell.alignment = Object.assign({}, mergedCell.alignment || {}, {
                vertical: 'middle',
                horizontal: 'center',
                wrapText: true
            });
        } else {
            const singleCell = sheet.getCell(`A${dayStart}`);
            singleCell.alignment = Object.assign({}, singleCell.alignment || {}, {
                vertical: 'middle',
                horizontal: 'center',
                wrapText: true
            });
        }
    }

    const buffer = await workbook.xlsx.writeBuffer();
    saveAs(new Blob([buffer]), "Schedule.xlsx");
}
