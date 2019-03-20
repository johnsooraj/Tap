import { UserPermission } from "../common-enums/user-permissions";

export class UserPermissions {

    constructor(permission: UserPermission) {
        this.securityPermissionId = permission;
    }

    roleId: number;
    permissionId: number;
    permission: string;
    securityPermissionId: number;
    permissionUC: string;
}