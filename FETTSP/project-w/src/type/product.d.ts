import {Image} from "./image";

export interface Product {
    id: number;
    name: string;
    description: string;
    idCategory: number;
    price: string;
    quantity:number;
    imageDTOs: Image[];
}