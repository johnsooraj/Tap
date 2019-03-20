export class CommonAdminPermissions {
    view: boolean;
    create: boolean;
    delete: boolean;
    update: boolean;
    all: boolean;
      
    constructor() {
        this.view = false;
        this.create = false;
        this.delete = false;
        this.update = false;
        this.all = false;
    }
}