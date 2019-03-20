import { FeedbackObject } from "./feedback";

export class FeedbackGroupObject {
    id: number;
    feedbackFormName: string;
    status: number;
    organizationId: number;
    timestamp: number;
    createDate: number;
    feedbacks: FeedbackObject[];
    exprieDate: number;

    checkboxStatus: boolean;
}