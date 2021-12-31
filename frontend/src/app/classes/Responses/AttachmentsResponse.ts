export class AttachmentResponse{
    file?: File;
    link: string;
    name?: string;
    constructor(file?: File, name?:string){
        this.file = file;
        this.link = '';
        this.name = name;

    }
}