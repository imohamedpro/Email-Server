export class AttachmentResponse{
    file: File;
    link: string;
    constructor(file: File){
        this.file = file;
        this.link = 'https://www.youtube.com/watch?v=dQw4w9WgXcQ';
    }
}