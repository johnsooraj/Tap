import { CommonAdminPermissions } from "./common-permissions";

export class AdminPermissionModal {
    customer: CommonAdminPermissions;
    admin: CommonAdminPermissions;
    feedback: CommonAdminPermissions;
    poll: CommonAdminPermissions;
    notice: CommonAdminPermissions;
    profile: CommonAdminPermissions;
    downloadResult: CommonAdminPermissions;
    viewResults: CommonAdminPermissions;

    constructor() {
        this.customer = new CommonAdminPermissions();
        this.admin = new CommonAdminPermissions();
        this.feedback = new CommonAdminPermissions();
        this.poll = new CommonAdminPermissions();
        this.notice = new CommonAdminPermissions();
        this.profile = new CommonAdminPermissions();
        this.downloadResult = new CommonAdminPermissions();
        this.viewResults = new CommonAdminPermissions();
    }
}