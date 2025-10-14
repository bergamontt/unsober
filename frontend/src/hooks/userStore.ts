import { create } from "zustand";
import type { Student } from "../models/Student";
import { getStudentByEmail } from "../services/StudentService";

interface UserStore {
    user: Student | null;
    loadingUser: boolean;
    error: string | null;
    fetchByEmail: (email: string) => Promise<void>;
    clearUser: () => void;
}

export const useUserStore = create<UserStore>((set) => ({
    user: null,
    loadingUser: false,
    error: null,

    fetchByEmail: async (username: string) => {
        set({ loadingUser: true, error: null });
        try {
            const data = await getStudentByEmail(username);
            set({ user: data });
        } catch (err) {
            set({ error: err instanceof Error ? err.message : "Unknown error" });
        } finally {
            set({ loadingUser: false });
        }
    },

    clearUser: () => set({ user: null, error: null }),
}));