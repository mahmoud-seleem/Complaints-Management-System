package com.example.Complaints.Management.System.Controllers;

//@RestController
//@RequestMapping("/api/complaint")
public class CompController {

//    @Autowired
//    private CompService compService;

//    @PostMapping("/new")
//    public CompDto createNewComplaint(@RequestBody CompDto compDto) throws IllegalAccessException {
//        return  compService.createNewComplaint(compDto);
//    }
//    @PutMapping("/edit")
//    public CompDto editComplaint(@RequestBody CompDto compDto) throws IllegalAccessException {
//        return  compService.editComplaint(compDto);
//    }
//    @PutMapping("/change-status")
//    public CompDto changeComplaintStatus(
//            @PathParam("adminId") Long adminId,
//            @PathParam("compId") Long compId,
//            @PathParam("newType") String newType) throws IllegalAccessException {
//        return  compService.changeComplaintStatus(adminId,compId,newType);
//    }
//
//    @PutMapping("/change-assignee")
//    public CompDto changeComplaintAssignee(
//            @PathParam("adminId") Long adminId,
//            @PathParam("compId") Long compId,
//            @PathParam("assignee")Long assignee) throws IllegalAccessException {
//        return  compService.changeComplaintAssignee(adminId,compId,assignee);
//    }
//
//    @GetMapping("/{id}")
//    public CompDto getComplaint(@PathVariable("id") Long id ) throws IllegalAccessException {
//        return  compService.getComplaintById(id);
//    }
//    @GetMapping("/history/{id}")
//    public List<CompStatusDto> getComplaintHistory(@PathVariable("id") Long id ) throws IllegalAccessException {
//        return  compService.getComplaintHistory(id);
//    }

//    @GetMapping("/user/{id}")
//    public List<CompDto> getAllUserComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
//        return  compService.getAllUserComplaints(id);
//    }
//    @GetMapping("/admin/{id}")
//    public List<CompDto> getAllAdminAssignedComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
//        return  compService.getAllAdminAssignedComplaints(id);
//    }
//
//    @DeleteMapping("/delete")
//    public List<CompDto> deleteUserComplaint(@PathParam("userId") Long userId,@PathParam("compId") Long compId) throws IllegalAccessException {
//        return  compService.deleteComplaint(userId,compId);
//    }
    // Fetch NEXT complaints (oldest to newest)
//    @GetMapping("/user/{userId}/next")
//    public List<CompDto> getUserNextComplaints(
//            @PathVariable Long userId,
//            @RequestParam(required = false) String cursor,
//            @RequestParam(defaultValue = "2") int size,
//            @RequestParam(required = false) String status) throws IllegalAccessException {
//        return compService.getUserNextComplaints(userId,cursor,size,status,false);
//    }
//
//    @GetMapping("/user/{userId}/prev")
//    public List<CompDto> getUserPrevComplaints(
//            @PathVariable Long userId,
//            @RequestParam(required = false) String cursor,
//            @RequestParam(defaultValue = "2") int size,
//            @RequestParam(required = false) String status) throws IllegalAccessException {
//        return compService.getUserPrevComplaints(userId,cursor,size,status,false);
//    }
//    @GetMapping("/admin/{userId}/next")
//    public List<CompDto> getAdminNextComplaints(
//            @PathVariable Long userId,
//            @RequestParam(required = false) String cursor,
//            @RequestParam(defaultValue = "2") int size,
//            @RequestParam(required = false) String status) throws IllegalAccessException {
//        return compService.getUserNextComplaints(userId,cursor,size,status,true);
//    }
//
//    @GetMapping("/admin/{userId}/prev")
//    public List<CompDto> getAdminPrevComplaints(
//            @PathVariable Long userId,
//            @RequestParam(required = false) String cursor,
//            @RequestParam(defaultValue = "2") int size,
//            @RequestParam(required = false) String status) throws IllegalAccessException {
//        return compService.getUserPrevComplaints(userId,cursor,size,status,true);
//    }
}
