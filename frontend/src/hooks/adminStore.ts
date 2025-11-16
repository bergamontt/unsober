import { createUserStore } from "./userStore.ts";
import type { Admin } from "../models/Admin.ts";
import { getAdminByEmail } from "../services/AdminService.ts";

export const useAdminStore = createUserStore<Admin>(getAdminByEmail);