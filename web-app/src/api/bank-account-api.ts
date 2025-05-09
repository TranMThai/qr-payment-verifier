import axios from "axios";
import { BASE_API } from "./base_api";

export const callBankAccount = async () => {
    const response = await axios({
        method: 'GET',
        url: `${BASE_API}/api/bank-account`
    })
    return response.data
}