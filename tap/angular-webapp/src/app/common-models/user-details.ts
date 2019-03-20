import { Address } from "./address";

export class UserDetails{
    userId: number;
    firstName: string;
    lastName: string;
    phone: string;
    email: string;
    age?: any;
    gender?: any;
    status: string;
    createdDate: number;
    attributeName?: any;
    lastModifiedDate: number;
    profilePic?: any;
    coverPic?: any;
    accountType?: any;
    profilePic20?: any;
    dateOfBirth: number;
    userAddress: Address;
    userInterest: any[];
    password?: any;
    profilePhoto?: any;
    coverPhoto?: any;
    createdPollCount?: any;
    publicPollResponseCount?: any;
    privatePollResponseCount?: any;
    checkBoxStatusForAddAdmin: boolean;
}