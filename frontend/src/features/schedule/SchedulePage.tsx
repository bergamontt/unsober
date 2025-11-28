import { useNavigate } from "react-router";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import { useAuthStore } from "../../hooks/authStore";
import Schedule from "./Schedule.tsx";
import { useEffect } from "react";

function SchedulePage() {
    const { isAuthenticated, loadingAuth, currentRoles } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if ((!isAuthenticated || !currentRoles.includes("STUDENT")) && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, navigate]);
    return(
        <PageWrapper>
            <Schedule />
        </PageWrapper>
    );
}

export default SchedulePage