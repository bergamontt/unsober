import { create } from "zustand";
import { useUserDetailsStore } from "./userDetailsStore";

interface User {
    firstName: string;
    lastName: string;
    patronymic: string;
    email: string;
}

interface UserStore<TUser extends User> {
    user: TUser | null;
    loadingUser: boolean;
    error: string | null;
    fetchByEmail: (email: string) => Promise<void>;
    clearUser: () => void;
}

export function createUserStore<TUser extends User>(
    getByEmail: (email: string) => Promise<TUser>
) {
    return create<UserStore<TUser>>((set) => ({
        user: null,
        loadingUser: false,
        error: null,

        fetchByEmail: async (email: string) => {
            set({ loadingUser: true, error: null });
            try {
                const data = await getByEmail(email);
                set({ user: data });

                const details = useUserDetailsStore.getState();
                details.setFirstName(data?.firstName ?? null);
                details.setLastName(data?.lastName ?? null);
                details.setPatronymic(data?.patronymic ?? null);
                details.setEmail(data?.email ?? null);
            } catch (err) {
                set({
                    error: err instanceof Error ? err.message : "Unknown error",
                });
            } finally {
                set({ loadingUser: false });
            }
        },

        clearUser: () => {
            set({ user: null, error: null });
            useUserDetailsStore.getState().clear();
        },
    }));
}
