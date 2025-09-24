import {Accordion} from "@mantine/core";
import SubjectItem from "./SubjectItem.tsx";
import SubjectPanel from "./SubjectPanel.tsx";

function SubjectPreview() {
    return(
        <Accordion >
            <Accordion.Item key="foo" value="foo">
                <Accordion.Control>
                    <SubjectItem
                        name="Основи коп'ютерних алгоритмів"
                        speciality="ФІ"
                        isRecommended={true}
                    />
                </Accordion.Control>
                <Accordion.Panel>
                    <SubjectPanel/>
                </Accordion.Panel>
            </Accordion.Item>
        </Accordion>
    );
}

export default SubjectPreview