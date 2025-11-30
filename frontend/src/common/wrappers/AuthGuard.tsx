import { useNavigate } from "react-router";
import { useAuthStore } from "../../hooks/authStore";
import type { UserRole } from "../../models/Auth";
import { useEffect } from "react";

interface AuthGuardProps {
    roles?: UserRole[];
    children: React.ReactNode;
}

function AuthGuard({ roles, children }: AuthGuardProps) {
    const { isAuthenticated, loadingAuth, currentRoles } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        const hasRequiredRole = roles ?
            currentRoles.some((r: UserRole) => roles.includes(r)) : true;
        if (!isAuthenticated || !hasRequiredRole || loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, currentRoles, navigate]);

    return <>{children}</>;
};

export default AuthGuard;