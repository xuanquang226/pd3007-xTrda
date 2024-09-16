import {Image} from "./image";

interface Product {
    id: number;
    name: string;
    description: string;
    idCategory: number;
    imageDTOs: Image[];
}