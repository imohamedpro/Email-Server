export class EmailRequest{
    date!: Date;
    from!: string;
    priority!: number;
    subject!: string;
    content!: string;
    attachmentsLinks!: string[];
}