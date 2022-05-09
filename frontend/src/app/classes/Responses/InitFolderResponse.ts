import { EmailHeaderResponse } from "./EmailHeaderResponse"

export class InitFolderResponse{
    emailsHeader!: EmailHeaderResponse[];
    numberOfPages!: number;
}