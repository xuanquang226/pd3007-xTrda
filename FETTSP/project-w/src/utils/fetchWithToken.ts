import { getTokenFromCookie } from "./token-utils";

interface RequestOptions {
    method?: string;
    body?: any;
    headers?: { [key: string]: string };
}

export default async function fetchWithToken(url:string, options: RequestOptions = {}){
    const token = getTokenFromCookie();
    const headers = {
        ...options.headers,
        'Content-type': 'application/json',
        Authorization: token ? token : "",
    };

    const response = await fetch(url, {
        ...options,
        headers,
    });

    return response;
}