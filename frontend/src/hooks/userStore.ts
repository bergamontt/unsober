import { create } from "zustand";

interface UserStore<User> {
    user: User | null;
    loadingUser: boolean;
    error: string | null;
    fetchByEmail: (email: string) => Promise<void>;
    clearUser: () => void;
}

function useUserStore<User>(getByEmail: (email: string) => Promise<User>) {
    return create<UserStore<User>>((set) => ({
        user: null,
        loadingUser: false,
        error: null,

        fetchByEmail: async (email: string) => {
            set({ loadingUser: true, error: null });
            try {
                const data = await getByEmail(email);
                set({ user: data });
            } catch (err) {
                set({ error: err instanceof Error ? err.message : "Unknown error" });
            } finally {
                set({ loadingUser: false });
            }
        },

        clearUser: () => set({ user: null, error: null }),
    }));
}

export default useUserStore;