import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, } from '@angular/common/http';
import { Organization } from '../common-models/organization';
import { CommonLoginService } from '../common-login/common-login.service';
import { Observable } from 'rxjs';
import { User } from '../common-models/user';
import { Router } from '@angular/router';
import { FeedbackObject } from '../common-models/feedback';
import { PollObject } from '../common-models/poll';
import { NoticeObject } from '../common-models/notice';
import { FeedbackGroupObject } from '../common-models/feedback-group';

const ROOT_URL_ADD_CUSTOMER = '/tap/api/admin/organization/addmember/';
const ROOT_URL_REMOVE_CUSTOMER = '/tap/api/admin/organization/removemember';
const ROOT_URL_ORGANIZATION = '/tap/api/admin/organization';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  organizationModel: Organization;
  routerRedirectStatus: boolean = true;

  constructor(
    private http: HttpClient,
    private commonService: CommonLoginService,
    private router: Router
  ) {
    this.organizationModel = this.commonService.userData.organization;
  }

  getOrganizationByOrganizationId() {
    let organizationId = this.commonService.userData.organization.organizationId;
    return this.http.get<Organization>(ROOT_URL_ORGANIZATION + '/' + organizationId);
  }

  addCustomerToOrganizationByOrgId(data: any): Observable<any> {
    return this.http.post(ROOT_URL_ADD_CUSTOMER + this.organizationModel.organizationId, data);
  }

  removeCustomerFromOrganization(data: any): Observable<number[]> {
    return this.http.post<number[]>(ROOT_URL_REMOVE_CUSTOMER, data);
  }



  updateOrganization(data: Organization): Observable<Organization> {
    return this.http.put<Organization>(ROOT_URL_ORGANIZATION, data);
  }

  getOrganizationAdministratorByOrgId(): Observable<User[]> {
    const options = { params: new HttpParams().set('type', 'administrator') };
    return this.http.get<User[]>(ROOT_URL_ORGANIZATION + `/${this.organizationModel.organizationId}`, options);
  }

  getOrganizationMembersByOrgId(): Observable<User[]> {
    const options = { params: new HttpParams().set('type', 'members') };
    return this.http.get<User[]>(ROOT_URL_ORGANIZATION + `/${this.organizationModel.organizationId}`, options);
  }

  getOrganizationFeedbacksByOrgId(): Observable<FeedbackGroupObject[]> {
    const options = { params: new HttpParams().set('type', 'feedbacks') };
    return this.http.get<FeedbackGroupObject[]>(ROOT_URL_ORGANIZATION + `/${this.organizationModel.organizationId}`, options);
  }

  getOrganizationFeedbackGroupByOrgId(): Observable<FeedbackGroupObject[]> {
    const options = { params: new HttpParams().set('type', 'feedbackgroup') };
    return this.http.get<FeedbackGroupObject[]>(ROOT_URL_ORGANIZATION + `/${this.organizationModel.organizationId}`, options);
  }

  getOrganizationPollsByOrgId(): Observable<PollObject[]> {
    const options = { params: new HttpParams().set('type', 'polls') };
    return this.http.get<PollObject[]>(ROOT_URL_ORGANIZATION + `/${this.organizationModel.organizationId}`, options);
  }

  getOrganizationNoticesByOrgId(): Observable<NoticeObject[]> {
    const options = { params: new HttpParams().set('type', 'notices') };
    return this.http.get<NoticeObject[]>(ROOT_URL_ORGANIZATION + `/${this.organizationModel.organizationId}`, options);
  }

  getFeedbackById(id): Observable<FeedbackObject> {
    return this.http.get<FeedbackObject>(`${ROOT_URL_ORGANIZATION}/feedback/${id}`);
  }

  getPollById(id): Observable<PollObject> {
    return this.http.get<PollObject>(`${ROOT_URL_ORGANIZATION}/poll/${id}`);
  }

  getNoticeById(id): Observable<NoticeObject> {
    return this.http.get<NoticeObject>(`${ROOT_URL_ORGANIZATION}/notice/${id}`);
  }

  createSubAdminForOrganization(user: User): Observable<User> {
    return this.http.post<User>(ROOT_URL_ORGANIZATION + '/addadmin', user);
  }

  updateSubAdminForOrganization(user: User): Observable<User> {
    return this.http.post<User>(ROOT_URL_ORGANIZATION + '/updateadmin', user);
  }

  fetchFeedbackById(id): Observable<FeedbackObject> {
    return this.http.get<FeedbackObject>(`${ROOT_URL_ORGANIZATION}/feedback/` + id);
  }

  fetchFeedbackGroupById(id): Observable<FeedbackGroupObject> {
    return this.http.get<FeedbackGroupObject>(`${ROOT_URL_ORGANIZATION}/feedbackgroup/` + id);
  }

  fetchPollkById(id): Observable<FeedbackObject> {
    return this.http.get<FeedbackObject>(`${ROOT_URL_ORGANIZATION}/poll/` + id);
  }

  fetchNoticeById(id): Observable<FeedbackObject> {
    return this.http.get<FeedbackObject>(`${ROOT_URL_ORGANIZATION}/notice/` + id);
  }

  saveSubscriptionByOrganizationModel(data: Organization): Observable<any> {
    return this.http.post<any>(`/tap/api/admin/subscriptiontool`, data);
  }

  updateSubscriptionByOrganizationModel(data: Organization): Observable<any> {
    return this.http.put<any>(`/tap/api/admin/subscriptiontool`, data);
  }

  deleteFeedbacksById(ids): Observable<number[]> {
    return this.http.post<number[]>(`${ROOT_URL_ORGANIZATION}/feedback/delete`, ids);
  }

  deleteFeedbackGroupById(ids): Observable<number[]> {
    return this.http.post<number[]>(`${ROOT_URL_ORGANIZATION}/feedbackgroup/delete`, ids);
  }

  deletePollsById(ids): Observable<number[]> {
    return this.http.post<number[]>(`${ROOT_URL_ORGANIZATION}/poll/delete`, ids);
  }

  deleteNoticesById(ids): Observable<number[]> {
    return this.http.post<number[]>(`${ROOT_URL_ORGANIZATION}/notice/delete`, ids);
  }

  fetchSubscriptionResults(fetch: string, page: number, from?: any, to?: any): Observable<any[]> {
    const options = {
      params: new HttpParams()
        .set('fetch', fetch)
        .set('page', page.toString())
        .set('count', '10')
        .set('upperlimit', from)
        .set('lowerlimit', to)
    };
    return this.http.get<any[]>(`/tap/api/admin/results/${this.organizationModel.organizationId}`, options);
  }
}
