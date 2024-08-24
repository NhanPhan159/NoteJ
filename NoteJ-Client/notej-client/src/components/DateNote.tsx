import { CSSProperties, FC, ReactNode, useContext, useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogFooter
} from "@/components/ui/dialog"
import { Button } from "./ui/button";
import { Textarea } from "./ui/textarea";
import axios from "axios";
import { TNote } from "@/type";
import { daysOfWeek } from "../constants";
import { HomeContext } from "@/pages/home";

const DateNote:FC<TNote> = (props) => {
    const [dateAndDay,setDateAndDate] = useState("")
    const d = new Date()
    const styleForOldday:CSSProperties = {border:"solid 1px red", borderRadius: "5px",padding:"5px 25px", flexBasis:"24%"}
    if(d.toISOString().includes(props.date)){
      styleForOldday.border = "solid 1px blue"
    }
    else if(props.date < d.toISOString()){
      styleForOldday.border = "solid 1px gray"
      styleForOldday.opacity = "75%"
    }

    // function
    async function updateNote(note:TNote){
      const res = await axios.put(`http://localhost:8080/notes/${note.id}`,{content:note.content})
    }
    async function createNote(note:TNote) {
      const res = await axios.post("http://localhost:8080/notes",{date:note.date,content:note.content})
    }
    async function hanldeClick(note:TNote) {
      if(note.id)
        await updateNote(note)
      else
        await createNote(note)
    }

    // help remain the format of note content
    // need to improve
    function covertContentToList(text: string){
      if(text !== ''){
        const slideText = text.slice(0,200)
        const textArray = slideText.split("\n")
        const textArrayAfterFormat:any[] = []
        textArray.forEach((element,index) => {
          if(index === textArray.length - 1 && text.length > 200){
            textArrayAfterFormat.push(<p>{element + ' ... (more)'}</p>)
          } else
          textArrayAfterFormat.push(<p>{element}</p>)

        });
        return textArrayAfterFormat
      }
      return []
    }

    // side effect
    useEffect(()=>{
        const temp = new Date(props.date)
        setDateAndDate(temp.getDate() + ", " + daysOfWeek[temp.getDay()])
    },[props])
    return ( 
      <DialogCustom note={props} saveFunc={hanldeClick}>
        <div className="flex-" style={styleForOldday}>
              <h2 className="text-center text-xl font-bold mb-2">{dateAndDay}</h2>
              {covertContentToList(props.content).map(curr=>(curr))}
          </div>
      </DialogCustom>
     );
}

type TDialog = {
  children: ReactNode,
  note: TNote,
  saveFunc: (note:TNote) => void
}
const DialogCustom:FC<TDialog> = (props) => {

  // data
  const [note,setNote] = useState<TNote>({})
  const [open, setOpen] = useState(false)
  const setUpdateOrCreateNote = useContext(HomeContext)

  // function
  function handleTextChange(content: string) {
    const newContentNote = {...note,content}
    setNote(newContentNote)
  }
  function handleSave(){
    props.saveFunc(note)
    setOpen(false)
    setUpdateOrCreateNote()
  }

  // side effect
  useEffect(() => {
    setNote(props.note)
  },[])
  
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        {props.children}
      </DialogTrigger>
      <DialogContent className="">
        <DialogHeader>
        </DialogHeader>
        <div>
          < Textarea value={note.content} onChange={(e)=>handleTextChange(e.target.value)} className="h-72" placeholder="Type your message here." />
        </div>
      <DialogFooter>
        <Button type="submit" onClick={handleSave}>Save changes</Button>
      </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}
 
export default DateNote;