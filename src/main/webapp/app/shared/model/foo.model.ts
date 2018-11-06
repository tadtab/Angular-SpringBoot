export interface IFoo {
    id?: string;
    bar?: string;
}

export class Foo implements IFoo {
    constructor(public id?: string, public bar?: string) {}
}
