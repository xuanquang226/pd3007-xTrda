import {Image} from "./image";

export interface Product {
    id: number;
    name: string;
    description: string;
    idCategory: number;
    imageDTOs: Image[];
}