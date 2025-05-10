import { useEffect, useState } from 'react'
import { callBankAccount } from '../api/bank-account-api'
import type { BankAccount } from '../types/bank-account'

const BankQR = () => {
    const [qrUrl, setQrUrl] = useState<string>("https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif")

    useEffect(() => {
        const getQrUrl = async () => {
            const bankAccount: BankAccount = await callBankAccount()
            setQrUrl(`https://img.vietqr.io/image/${bankAccount.bankShortName}-${bankAccount.accountNumber}-print.jpg?accountName=${bankAccount.accountHolderName}`)
        }
        getQrUrl()
    }, [])

    return (
        <div style={{ width: 500, height: 700 }}>
            <img src={qrUrl} alt="" width="100%"/>
        </div>
    )
}

export default BankQR