import { getTokenFromCookie, setTokenToCookie, setRefreshTokenToCookie, getRefreshTokenFromCookie } from "./token-utils";
import TupleToken from "@/type/tuple-token";

interface RequestOptions {
    method?: string;
    body?: any;
    headers?: { [key: string]: string };
}

export default async function fetchWithToken(url:string, options: RequestOptions = {}, autoRetry: boolean){
    const url2 = process.env.NEXT_PUBLIC_API_URL;
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

    // Kiem tra response ngoai le jwtexpired
    if (response.status === 403 && autoRetry) {
            const responseValidateRefreshToken = await fetch(`https://${url2}/api/account/auth`, {
                method: 'GET',
                headers: {
                    "Authorization2": `${getRefreshTokenFromCookie()}`,
                },
            });

            if (responseValidateRefreshToken.ok) {               
                const data: TupleToken = await responseValidateRefreshToken.json();
                if(data.refreshToken.trim().toLowerCase() === "empty"){
                    return null;
                }
                setTokenToCookie(data.accessToken);
                setRefreshTokenToCookie(data.refreshToken);

                const retryHeaders = {
                    ...headers,
                    Authorization: data.accessToken,
                };

                return await fetch(url, {
                    ...options,
                    headers: retryHeaders,
                });   
            }               
    }
    return response;
}