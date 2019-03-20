import { User } from "./user";
import { Address } from "./address";
import { FeedbackObject } from "./feedback";
import { FeedbackGroupObject } from "./feedback-group";

export class Organization {
    organizationId: number;
    organizationName: string;
    coverPhoto: string;
    profilePhoto: string;
    email: string;
    timestamp: string;
    createDateTime: string;
    organizationAddress: Address;
    members: User[];
    coverPhotoByte: any;
    profilePhotoByte: any;
    authorityName: string;
    notices: any[];
    polls: any[];
    feedbacks: FeedbackObject[];
    feedbackGroups: FeedbackGroupObject[];

    checkboxStatus: boolean;
}