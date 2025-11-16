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

function useUserStore<TUser extends User>(getByEmail: (email: string) => Promise<TUser>) {
    const { setFirstName, setLastName, setPatronymic, setEmail, clear } = useUserDetailsStore();
    return create<UserStore<TUser>>((set) => ({
        user: null,
        loadingUser: false,
        error: null,

        fetchByEmail: async (email: string) => {
            set({ loadingUser: true, error: null });
            try {
                const data = await getByEmail(email);
                set({ user: data });
                setFirstName(data?.firstName ?? null);
                setLastName(data?.lastName ?? null);
                setPatronymic(data?.patronymic ?? null);
                setEmail(data?.email ?? null);
            } catch (err) {
                set({ error: err instanceof Error ? err.message : "Unknown error" });
            } finally {
                set({ loadingUser: false });
            }
        },

        clearUser: () => {
            set({ user: null, error: null });
            clear();
        }
    }));
}

export default useUserStore;