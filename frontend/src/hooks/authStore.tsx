import { jwtDecode } from "jwt-decode";
import { useState, useEffect, useRef, createContext, useContext, type ReactNode } from "react";
import { useAdminStore } from "./adminStore.ts";
import { useStudentStore } from "./studentStore.ts";
import { useUserDetailsStore } from "./userDetailsStore.ts";
import { UserRole } from "../models/Auth.ts";

const tokenKey = "unsoberJwt";

interface AuthStore {
    token: string | null,
    setToken: (token: string) => void,
    removeToken: () => void,
    loadingAuth: boolean,
    isAuthenticated: boolean,
    currentEmail: string | null
    currentRoles: UserRole[]
}

function isValid(token: string | null): boolean {
    if (token == null)
        return false;
    try {
        const { exp } = jwtDecode<{ exp?: number }>(token);
        if (exp == undefined)
            return false;
        return Date.now() < exp * 1000;
    } catch {
        return false;
    }
}

function getEmailFromToken(token: string | null): string | null {
    if (token == null)
        return null;
    try {
        const { sub } = jwtDecode<{ sub?: string }>(token);
        return sub ?? null;
    } catch {
        return null;
    }
}

export function getRolesFromToken(token: string | null): UserRole[] {
    if (token == null)
        return [];
    try {
        const payload = jwtDecode<{ roles?: UserRole[]; }>(token);
        if (payload.roles && Array.isArray(payload.roles))
            return payload.roles;
        return [];
    } catch {
        return [];
    }
}

function getTimeToExpiration(token: string | null): number | null {
    if (token == null)
        return null;
    try {
        const { exp } = jwtDecode<{ exp?: number }>(token);
        if (exp == null)
            return null;
        const now = Date.now();
        return exp * 1000 - now;
    } catch {
        return null;
    }
}

export function getValidToken(): string | null {
    const storedToken = localStorage.getItem(tokenKey);
    return isValid(storedToken) ? storedToken : null;
}

function storeToken(token: string) {
    if (isValid(token))
        localStorage.setItem(tokenKey, token);
    else
        localStorage.removeItem(tokenKey);
}

function deleteToken() {
    localStorage.removeItem(tokenKey);
}

const AuthContext = createContext<AuthStore | null>(null);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const { fetchByEmail: fetchAdminByEmail, clearUser: clearAdmin } = useAdminStore();
    const { fetchByEmail: fetchStudentByEmail, clearUser: clearStudent } = useStudentStore();
    const { clear: clearUserInfo } = useUserDetailsStore();

    const [token, setTokenState] = useState<string | null>(null);
    const [loadingAuth, setLoadingAuth] = useState<boolean>(true);
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [currentEmail, setCurrentEmail] = useState<string | null>(null);
    const [currentRoles, setCurrentRoles] = useState<UserRole[]>([]);
    const expirationTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

    const updateStateFromToken = (token: string | null) => {
        if (expirationTimerRef.current != null) {
            clearTimeout(expirationTimerRef.current);
            expirationTimerRef.current = null;
        }

        const emailFromToken = getEmailFromToken(token);
        const rolesFromToken = getRolesFromToken(token);
        const authenticated = token != null;
        setTokenState(token);
        setIsAuthenticated(authenticated);
        setCurrentEmail(emailFromToken);
        setCurrentRoles(rolesFromToken);

        if (emailFromToken) {
            if (rolesFromToken.includes(UserRole.STUDENT)) {
                fetchStudentByEmail(emailFromToken);
            } else {
                clearStudent();
            }

            if (rolesFromToken.includes(UserRole.ADMIN)) {
                fetchAdminByEmail(emailFromToken);
            } else {
                clearAdmin();
            }
        } else {
            clearUserInfo();
        }

        const msToExpiration = getTimeToExpiration(token);
        if (msToExpiration != null && msToExpiration > 0) {
            expirationTimerRef.current = setTimeout(() => {
                deleteToken();
                updateStateFromToken(null);
            }, msToExpiration);
        }
    }

    const setToken = (token: string) => {
        storeToken(token);
        updateStateFromToken(getValidToken());
    }

    const removeToken = () => {
        deleteToken();
        updateStateFromToken(null);
    }

    useEffect(() => {
        const syncToken = () => {
            setLoadingAuth(true);
            const latest = getValidToken();
            updateStateFromToken(latest);
            setLoadingAuth(false);
        };

        syncToken();
        window.addEventListener('storage', syncToken);
        return () => {
            window.removeEventListener('storage', syncToken);
            if (expirationTimerRef.current != null)
                clearTimeout(expirationTimerRef.current);
        }
    }, []);

    const value: AuthStore = { token, setToken, removeToken, loadingAuth, isAuthenticated, currentEmail, currentRoles };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export function useAuthStore(): AuthStore {
    const ctx = useContext(AuthContext);
    if (!ctx) 
        throw new Error("useAuthStore must be used within AuthProvider");
    return ctx;
}
