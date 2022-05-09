export class EmailRequest{
    date!: string;
    from!: string;
    priority!: number;
    subject!: string;
    content!: string;
    attachments!: string[];
}