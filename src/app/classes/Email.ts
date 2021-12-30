import { EmailBody } from "./EmailBody";
import { EmailHeader } from "./EmailHeader";

export class Email {
    id!: number;
    isRead!: boolean;
    folders!: any[];
    emailHeader!: EmailHeader;
    emailBody!: EmailBody;
    deleteDate!: null;

    constructor(){
        this.emailHeader = new EmailHeader();
        this.emailBody = new EmailBody();
    }
}