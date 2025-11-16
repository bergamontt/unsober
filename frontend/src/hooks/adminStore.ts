import useUserStore from "./userStore.ts";
import type { Admin } from "../models/Admin.ts";
import { getAdminByEmail } from "../services/AdminService.ts";

export const useAdminStore = useUserStore<Admin>(getAdminByEmail);