import { createUserStore } from "./userStore.ts";
import type { Student } from "../models/Student.ts";
import { getStudentByEmail } from "../services/StudentService";

export const useStudentStore = createUserStore<Student>(getStudentByEmail);