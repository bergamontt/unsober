import { jwtDecode } from "jwt-decode";
import { useState, useEffect, useRef } from "react";
import { useAdminStore } from "./adminStore.ts";
import { useStudentStore } from "./studentStore.ts";

const tokenKey = "unsoberJwt";

interface AuthStore {
    token: string | null,
    setToken: (token: string) => void,
    removeToken: () => void,
    loadingAuth: boolean,
    isAuthenticated: boolean,
    currentEmail: string | null
    currentRoles: string[]
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

export function getRolesFromToken(token: string | null): string[] {
    if (token == null)
        return [];
    try {
        const payload = jwtDecode<{ roles?: string[]; }>(token);
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
}

function deleteToken() {
    localStorage.removeItem(tokenKey);
}

export function useAuthStore(): AuthStore {
    const { fetchByEmail: fetchAdminByEmail, clearUser: clearAdmin } = useAdminStore();
    const { fetchByEmail: fetchStudentByEmail, clearUser: clearStudent } = useStudentStore();

    const [token, setTokenState] = useState<string | null>(null);
    const [loadingAuth, setLoadingAuth] = useState<boolean>(true);
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [currentEmail, setCurrentEmail] = useState<string | null>(null);
    const [currentRoles, setCurrentRoles] = useState<string[]>([]);
    const expirationTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

    const updateStateFromToken = (token: string | null) => {
        if (expirationTimerRef.current != null) {
            clearTimeout(expirationTimerRef.current);
            expirationTimerRef.current = null;
        }

        const emailFromToken = getEmailFromToken(token);
        const rolesFromToken = getRolesFromToken(token);
        setTokenState(token);
        setIsAuthenticated(token != null);
        setCurrentEmail(emailFromToken);
        setCurrentRoles(rolesFromToken);
        if (emailFromToken && rolesFromToken.includes("STUDENT")) {
            fetchStudentByEmail(emailFromToken);
        } else {
            clearStudent();
        }

        if (emailFromToken && rolesFromToken.includes("ADMIN")) {
            fetchAdminByEmail(emailFromToken);
        } else {
            clearAdmin();
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

    return { token, setToken, removeToken, loadingAuth, isAuthenticated, currentEmail, currentRoles };
}