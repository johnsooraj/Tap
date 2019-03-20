import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { PagenotFoundComponent } from './pagenot-found/pagenot-found.component';
import { OrganizationComponent } from './organization/organization.component';
import { EditOrganizationComponent } from './edit-organization/edit-organization.component';

const routes: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
      {
        path:'',
        redirectTo:'organization',
        pathMatch:'full'
      },
      {
        path: 'organization',
        component: OrganizationComponent
      },
      {
        path: 'organization/:id',
        component: EditOrganizationComponent
      },
      {
        path: '**',
        component: PagenotFoundComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SuperAdminRoutingModule { }
