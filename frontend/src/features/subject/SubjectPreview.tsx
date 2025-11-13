import type { Subject } from "../../models/Subject.ts";
import {Accordion} from "@mantine/core";
import SubjectItem from "./SubjectItem.tsx";
import SubjectPanel from "./SubjectPanel.tsx";

interface SubjectPreviewProps {
    subject : Subject
}

function SubjectPreview({subject} : SubjectPreviewProps) {
    return(
        <Accordion >
            <Accordion.Item key={subject.id} value={subject.id}>
                <Accordion.Control>
                    <SubjectItem
                        name={subject.name}
                        speciality="ФІ"
                        isRecommended={true}
                    />
                </Accordion.Control>
                <Accordion.Panel>
                    <SubjectPanel subject={subject}/>
                </Accordion.Panel>
            </Accordion.Item>
        </Accordion>
    );
}

export default SubjectPreview