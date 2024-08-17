import { FC } from "react";
import { ArrowLeftIcon, ArrowRightIcon } from'@heroicons/react/16/solid'

type TTopBar = {
    nextFunc: () => void,
    prevFunc: () => void,
    monthYear: string
}

const TopBar:FC<TTopBar> = (props) => {
    return ( 
        <div className="flex justify-between items-center">
            <ArrowLeftIcon className="w-[28px] h-[28px]" onClick={props.prevFunc}/>
            <h1>{props.monthYear}</h1>
            <ArrowRightIcon className="w-[28px] h-[28px]" onClick={props.nextFunc}/>
        </div>
     );
}
 
export default TopBar;