import { MultipleOptionsQuestion } from "./multiple-options-question";
import { ImageObject } from "./image-options";
import { RatingObject } from "./rating-option";

export class PollObject {
    pollId: number;
    pollText: string;
    exprieDate: number;
    liveResult: boolean;
    colsePoll: boolean;
    timestamp: number;
    createDate: number;
    status: number;
    createdBy: number;
    createdUserName: string;
    pollImages: ImageObject[];
    questions: MultipleOptionsQuestion[];
    ratings: RatingObject[];
    pollType: number;

    checkboxStatus: boolean;
}