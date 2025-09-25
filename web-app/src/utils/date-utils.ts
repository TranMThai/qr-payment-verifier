export const dateToLocalDateTime = (date: Date) => {
    return date.toISOString().slice(0,19)
}