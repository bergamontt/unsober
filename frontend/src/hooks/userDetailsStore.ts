import { create } from "zustand";

interface UserDetailsStore {
    firstName: string | null;
    lastName: string | null;
    patronymic: string | null;
    email: string | null;
    setFirstName: (fstName: string | null) => void;
    setLastName: (lstName: string | null) => void;
    setPatronymic: (patronymic: string | null) => void;
    setEmail: (email: string | null) => void;
    clear: () => void;
}

export const useUserDetailsStore = create<UserDetailsStore>((set) => ({
        firstName: null,
        lastName: null,
        patronymic: null,
        email: null,

        setFirstName: (fstName: string | null) => {
            set({ firstName: fstName });
        },

        setLastName: (lstName: string | null) => {
            set({ lastName: lstName });
        },

        setPatronymic: (patronymic: string | null) => {
            set({ patronymic: patronymic });
        },

        setEmail: (email: string | null) => {
            set({ email: email });
        },

        clear: () => {
            set({ firstName: null, lastName: null, patronymic: null, email: null });
        }
    }));