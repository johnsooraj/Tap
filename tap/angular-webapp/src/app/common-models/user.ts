import { UserDetails } from "./user-details";
import { UserRoles } from "./user-roles";
import { UserPermissions } from "./user-permissions";
import { Organization } from "./organization";
import { LoginModel } from "./login-model";

export class User {
    memberId: number;
    memberType: number;
    notificationEnable: boolean;
    showResetPassword: boolean;
    timestamp: number;
    createDateTime: number;
    admin: boolean;
    userToken: string;
    organization: Organization;

    userData: UserDetails;
    userRoles: UserRoles[];
    userPermissions: UserPermissions[];
    credentials: LoginModel;

    checkboxStatus: boolean;
}