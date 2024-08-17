import { createContext, useContext, useEffect, useRef, useState } from "react";
import axios from  'axios'
import TopBar from "../components/TopBar";
import WeekNote from "../components/WeekNote";
import { months } from "../constants";
import { TNote } from "../type";

type THomeContext = {
    value: () => void
}
export const HomeContext = createContext(null)
const Home = () => {

    // data
    const [swicth,setSwicth] = useState(false)
    const currentDate = useRef(new Date())
    const [monthYearCurrent, setmonthYearCurrent] = useState<string>("")
    const [daysInWeek,setDaysInWeek] = useState<TNote[]>([])

    // function
    async function fetchData(currentDate: Date) {
        const response = await axios.get(`http://localhost:8080/notes/range?today=${currentDate.toISOString().split("T")[0]}`)
        setDaysInWeek(response.data)
    }
    function calculateDate(operation:string){
        if(operation === "plus"){
            currentDate.current.setDate(currentDate.current.getDate() + 7)
        }
        else if(operation === "subtract"){
            currentDate.current.setDate(currentDate.current.getDate()-7)
        }
    }
    function nextFunc() {
        calculateDate("plus")
        fetchData(currentDate.current)
        const monthYear = months[currentDate.current.getMonth()] + ", " + currentDate.current.getFullYear()
        setmonthYearCurrent(monthYear)
    }
    function prevFunc() {
        calculateDate("subtract")
        fetchData(currentDate.current)
        const monthYear = months[currentDate.current.getMonth()] + ", " + currentDate.current.getFullYear()
        setmonthYearCurrent(monthYear)
    }
    function setUpdateOrCreateNote(){
        setSwicth(!swicth)
    }

    // side effect
    useEffect(()=>{
        fetchData(currentDate.current)
        const monthYear = months[currentDate.current.getMonth()] + ", " + currentDate.current.getFullYear()
        setmonthYearCurrent(monthYear)
    },[swicth]) // need to optimize

    return ( 
        <HomeContext.Provider value={setUpdateOrCreateNote}>
            <TopBar nextFunc={nextFunc} prevFunc={prevFunc} monthYear={monthYearCurrent}/>
            <WeekNote data={daysInWeek}/>
        </HomeContext.Provider>
     );
}
 
export default Home;