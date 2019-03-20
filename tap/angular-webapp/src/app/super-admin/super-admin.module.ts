import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTablesModule } from 'angular-datatables';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SuperAdminRoutingModule } from './super-admin-routing.module';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { PagenotFoundComponent } from './pagenot-found/pagenot-found.component';
import { OrganizationComponent } from './organization/organization.component';
import { AdminHomeContentComponent } from './admin-home-content/admin-home-content.component';
import { SuperAdminService } from './super-admin.service';
import { EditOrganizationComponent } from './edit-organization/edit-organization.component';

@NgModule({
  imports: [
    CommonModule,
    SuperAdminRoutingModule,
    DataTablesModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    AdminHomeComponent,
    SidebarComponent,
    PagenotFoundComponent,
    OrganizationComponent,
    AdminHomeContentComponent,
    EditOrganizationComponent
  ],
  providers: [
    SuperAdminService
  ]
})
export class SuperAdminModule { }
