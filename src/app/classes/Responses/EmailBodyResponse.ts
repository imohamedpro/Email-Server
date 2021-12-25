import { AttachmentResponse } from "./AttachmentsResponse";

export class EmailBodyResponse{
    content: string;
    attachments: AttachmentResponse[];
}