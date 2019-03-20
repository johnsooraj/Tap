import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Organization } from '../common-models/organization';
import { OrganizationComponent } from './organization/organization.component';
import { UserDetails } from '../common-models/user-details';

const ROOT_URL_ORGANIZATION = '/tap/api/admin/organization';
const FETCH_ALL_USERS = '/tap/api/admin/users';
const FETCH_ALL_USERS_BY_NAME = '/tap/api/admin/users/name/';
const FETCH_ALL_USERS_BY_PHONE = '/tap/api/admin/users/phone/';

@Injectable({
  providedIn: 'root'
})
export class SuperAdminService {

  constructor(
    private http: HttpClient,
  ) { }

  getAllOrganization(): Observable<any> {
    return this.http.get<Organization>(ROOT_URL_ORGANIZATION);
  }

  getOrganizationById(id: number): Observable<Organization> {
    return this.http.get<Organization>(ROOT_URL_ORGANIZATION + '/' + id);
  }

  saveOrganization(org: Organization): Observable<Organization> {
    return this.http.post<Organization>(ROOT_URL_ORGANIZATION, org);
  }

  updateOrganization(org: Organization): Observable<Organization> {
    return this.http.put<Organization>(ROOT_URL_ORGANIZATION, org);
  }

  resetAdminPassword(id: number): Observable<any> {
    return this.http.put<any>(`/tap/api/admin/reset-organization//${id}`, null);
  }

  deleteSingleOrganization(id: number) {
    return this.http.delete(ROOT_URL_ORGANIZATION + '/' + id);
  }

  deleteMultipleOrganization(ids: any) {
    return this.http.post(ROOT_URL_ORGANIZATION + '/delete', ids);
  }

  getAllCustomer(): Observable<UserDetails[]> {
    return this.http.get<UserDetails[]>(FETCH_ALL_USERS);
  }

  getAllCustomerByFirstName(name: string): Observable<UserDetails> {
    return this.http.get<UserDetails>(FETCH_ALL_USERS_BY_NAME + name);
  }

  getAllCustomerByPhoneNumber(phone: string): Observable<UserDetails> {
    return this.http.get<UserDetails>(FETCH_ALL_USERS_BY_PHONE + phone);
  }
}
