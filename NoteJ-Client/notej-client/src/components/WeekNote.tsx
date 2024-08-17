import { FC } from "react";
import DateNote from "./DateNote";
import { TNote } from "../type";

type TProps = {
    data: TNote[]
}
const WeekNote:FC<TProps> = (props) => {
    return ( 
        <div className="" style={{margin: "auto",display:"flex",justifyContent:"center", gap: "20px", flexWrap:"wrap"}}>
            {props.data?.map( curr => (
                <DateNote id={curr.id} dayOfWeek={curr.dayOfWeek} content={curr.content} date={curr.date}/>
            ))}
        </div>
     );
}
 
export default WeekNote;