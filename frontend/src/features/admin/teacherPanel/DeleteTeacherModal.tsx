import DeleteModal from "../DeleteModal";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
}

function DeleteTeacherModal({ opened, close }: DeleteModalProps) {
    return(
        <DeleteModal
            opened={opened}
            close={close}
            message="Ви дійсно впевнені, що хочете виконати операцію видалення? Ця операція є незворотньою."
            denyLabel="Повернутися"
            confirmLabel="Видалити"
            loading={false}
            onConfirm={() => {}}
        />
    );
}

export default DeleteTeacherModal