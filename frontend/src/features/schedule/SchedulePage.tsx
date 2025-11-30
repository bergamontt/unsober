import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import Schedule from "./Schedule.tsx";
import AuthGuard from "../../common/wrappers/AuthGuard.tsx";
import { UserRole } from "../../models/Auth.ts";

function SchedulePage() {
    return (
        <AuthGuard roles={[UserRole.STUDENT]}>
            <PageWrapper>
                <Schedule />
            </PageWrapper>
        </AuthGuard>
    );
}

export default SchedulePage