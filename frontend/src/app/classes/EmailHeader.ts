export class EmailHeader {
    from!: string;
    to!: string[];
    subject!: string;
    date!: string;
    priority!: number;
    id!: number;
    isRead!: boolean;
}