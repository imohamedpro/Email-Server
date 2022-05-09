export class EmailBody {
    body!: string;
    attachments!: string[];
    constructor(){
        this.attachments = [];
    }
}