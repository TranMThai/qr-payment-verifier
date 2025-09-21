export const dateToLocalDateTime = (date: Date) => {
    return date.toISOString().replace("Z","")
}