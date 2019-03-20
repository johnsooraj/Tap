import { MultipleOptionsQuestion } from "./multiple-options-question";

export class FeedbackObject {
    feedbackId: number;
    feedbackText: string;
    feedbackCreaterId: number;
    feedbackCreaterName: string;
    feedbackType: number;
    timestamp: number;
    createDate: number;
    status: number;
    questions: MultipleOptionsQuestion[];
    ratings: any[];
    freeComments: any[];
    exprieDate: number;

    checkboxStatus: boolean;
}