package com.example.blooddonationapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.model.*

class AppViewModel : ViewModel() {
    // Donors data
    private val _donors = mutableStateListOf<Donor>().apply {
        addAll(getSampleDonors())
    }
    val donors: List<Donor> get() = _donors

    // Employees data
    private val _employees = mutableStateListOf<Employee>().apply {
        addAll(getSampleEmployees())
    }
    val employees: List<Employee> get() = _employees

    // Blood Requests data
    private val _bloodRequests = mutableStateListOf<BloodRequest>().apply {
        addAll(getSampleBloodRequests())
    }
    val bloodRequests: List<BloodRequest> get() = _bloodRequests

    // Search và filter cho Blood Requests
    var bloodRequestSearchQuery by mutableStateOf("")
    var bloodRequestStatusFilter by mutableStateOf("Tất cả")
    var bloodRequestBloodFilter by mutableStateOf("Tất cả")
    var bloodRequestUrgencyFilter by mutableStateOf("Tất cả")

    val filteredBloodRequests: List<BloodRequest>
        get() {
            val q = bloodRequestSearchQuery.trim().lowercase()
            return _bloodRequests.filter { request ->
                val matchName = request.patientName.contains(q, ignoreCase = true)
                val matchStatus = bloodRequestStatusFilter == "Tất cả" || request.status == bloodRequestStatusFilter
                val matchBlood = bloodRequestBloodFilter == "Tất cả" || request.bloodType == bloodRequestBloodFilter
                val matchUrgency = bloodRequestUrgencyFilter == "Tất cả" || request.urgency == bloodRequestUrgencyFilter
                matchName && matchStatus && matchBlood && matchUrgency
            }
        }

    // Search và filter cho Donors
    var donorSearchQuery by mutableStateOf("")
    var donorBloodFilter by mutableStateOf("Tất cả")

    // Search và filter cho Employees
    var employeeSearchQuery by mutableStateOf("")
    var employeeRoleFilter by mutableStateOf<String?>(null)

    // Filtered lists
    val filteredDonors: List<Donor>
        get() {
            val q = donorSearchQuery.trim().lowercase()
            return _donors.filter { donor ->
                val matchName = donor.name.contains(q, ignoreCase = true)
                val matchBlood = donorBloodFilter == "Tất cả" || donor.bloodType == donorBloodFilter
                matchName && matchBlood
            }
        }

    val filteredEmployees: List<Employee>
        get() {
            val q = employeeSearchQuery.trim().lowercase()
            return _employees.filter { emp ->
                val name = emp.name.lowercase().trim()
                val nameWords = name.split("\\s+".toRegex())
                val queryWords = q.split("\\s+".toRegex())

                val matchesQuery = q.isBlank() ||
                        queryWords.all { qw -> nameWords.any { it.contains(qw) } }

                val matchesRole = employeeRoleFilter == null || emp.role == employeeRoleFilter

                matchesQuery && matchesRole
            }
        }


    // --- CRUD Operations for Donors ---
    fun addDonor(donor: Donor) {
        _donors.add(donor)
    }

    fun updateDonor(index: Int, updatedDonor: Donor) {
        if (index in _donors.indices) {
            _donors[index] = updatedDonor
        }
    }

    fun deleteDonor(index: Int) {
        if (index in _donors.indices) {
            _donors.removeAt(index)
        }
    }

    fun addDonationHistory(donorIndex: Int, donationRecord: DonationRecord) {
        if (donorIndex in _donors.indices) {
            val donor = _donors[donorIndex]
            val updatedDonor = donor.copy(
                donationHistory = donor.donationHistory + donationRecord
            )
            _donors[donorIndex] = updatedDonor
        }
    }

    // --- CRUD Operations for Employees ---
    fun addEmployee(employee: Employee) {
        _employees.add(employee)
    }

    fun addEmployee(name: String, role: String, dept: String, note: String, phone: String = "", email: String = "", isBloodDonor: Boolean = false, donorId: String? = null) {
        val newEmployee = Employee(
            id = if (_employees.isEmpty()) 1 else _employees.maxOf { it.id } + 1,
            name = name,
            role = role,
            department = dept,
            note = note,
            phone = phone,
            email = email,
            isBloodDonor = isBloodDonor, // THÊM
            donorId = donorId // THÊM
        )
        _employees.add(newEmployee)
    }

    fun updateEmployee(updated: Employee) {
        val index = _employees.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            _employees[index] = updated
        }
    }

    fun deleteEmployee(id: Int) {
        _employees.removeAll { it.id == id }
    }

    fun getEmployeeById(id: Int): Employee? = _employees.find { it.id == id }



    // --- Integration: Link employee with donor ---
    fun linkEmployeeToDonor(employeeId: Int, donorId: String) {
        val employeeIndex = _employees.indexOfFirst { it.id == employeeId }
        if (employeeIndex != -1) {
            val employee = _employees[employeeIndex]
            val updatedEmployee = employee.copy(
                isBloodDonor = true,
                donorId = donorId
            )
            _employees[employeeIndex] = updatedEmployee
        }
    }

    fun getEmployeeByDonorId(donorId: String): Employee? {
        return _employees.find { it.donorId == donorId }
    }

    // --- CRUD Operations for Blood Requests ---
    fun addBloodRequest(
        patientName: String,
        bloodType: String,
        location: String,
        requiredMl: Int,
        status: String = "Pending",
        urgency: String = "Medium",
        contactPhone: String = "",
        hospital: String = "",
        notes: String = ""
    ) {
        val newRequest = BloodRequest(
            id = if (_bloodRequests.isEmpty()) 1 else _bloodRequests.maxOf { it.id } + 1,
            patientName = patientName,
            bloodType = bloodType,
            location = location,
            requiredMl = requiredMl,
            status = status,
            urgency = urgency,
            contactPhone = contactPhone,
            hospital = hospital,
            notes = notes
        )
        _bloodRequests.add(newRequest)
    }
    fun updateBloodRequestStatus(requestId: Int, newStatus: String) {
        val index = _bloodRequests.indexOfFirst { it.id == requestId }
        if (index != -1) {
            _bloodRequests[index] = _bloodRequests[index].copy(status = newStatus)
        }
    }

    fun deleteBloodRequest(requestId: Int) {
        _bloodRequests.removeAll { it.id == requestId }
    }

    fun getBloodRequestById(id: Int): BloodRequest? = _bloodRequests.find { it.id == id }


}

// Sample data functions
private fun getSampleEmployees(): List<Employee> {
    return listOf(
        Employee(
            id = 1,
            name = "Nguyễn Thị Hồng",
            role = "Nhân viên y tế",
            department = "Khoa Huyết học",
            phone = "0912345678",
            email = "hong.nt@hospital.com",
            isBloodDonor = true,
            donorId = "1"
        ),
        Employee(
            id = 2,
            name = "Trần Văn Minh",
            role = "Quản lý tại chỗ",
            department = "Phòng Tổ chức",
            phone = "0923456789",
            email = "minh.tv@hospital.com"
        ),
        Employee(
            id = 3,
            name = "Lê Thị Lan",
            role = "Nhân viên y tế",
            department = "Khoa Cấp cứu",
            phone = "0934567890",
            email = "lan.lt@hospital.com",
            isBloodDonor = true,
            donorId = "3"
        ),
        Employee(
            id = 4,
            name = "Phạm Văn Hùng",
            role = "Cán bộ cấp cao",
            department = "Ban Giám đốc",
            phone = "0945678901",
            email = "hung.pv@hospital.com"
        ),
        Employee(
            id = 5,
            name = "Hoàng Thị Mai",
            role = "Nhân viên y tế",
            department = "Khoa Huyết học",
            phone = "0956789012",
            email = "mai.ht@hospital.com"
        )
    )
}
// THÊM vào AppViewModel.kt - trong phần sample data functions
private fun getSampleDonors(): List<Donor> {
    return listOf(
        // 10 người nhóm máu A+
        Donor(
            name = "Nguyễn Văn An",
            age = 25,
            phone = "0912345678",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("15/01/2024", "Bệnh viện Chợ Rẫy", 350),
                DonationRecord("20/03/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Trần Thị Bích",
            age = 30,
            phone = "0923456789",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("10/02/2024", "Bệnh viện Nhân dân 115", 500)
            )
        ),
        Donor(
            name = "Lê Văn Cường",
            age = 28,
            phone = "0934567890",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("05/01/2024", "Bệnh viện Đại học Y Dược", 400),
                DonationRecord("12/04/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Phạm Thị Dung",
            age = 22,
            phone = "0945678901",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("18/03/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Hoàng Văn Đức",
            age = 35,
            phone = "0956789012",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("22/02/2024", "Bệnh viện Nhân dân Gia Định", 500),
                DonationRecord("30/04/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Đỗ Thị Hoa",
            age = 26,
            phone = "0967890123",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("08/01/2024", "Bệnh viện Từ Dũ", 400)
            )
        ),
        Donor(
            name = "Ngô Văn Hùng",
            age = 32,
            phone = "0978901234",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("14/03/2024", "Bệnh viện Nhi Đồng", 450)
            )
        ),
        Donor(
            name = "Bùi Thị Hạnh",
            age = 29,
            phone = "0989012345",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("25/02/2024", "Bệnh viện Chợ Rẫy", 350),
                DonationRecord("15/05/2024", "Viện Huyết học", 500)
            )
        ),
        Donor(
            name = "Đặng Văn Khánh",
            age = 27,
            phone = "0990123456",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("11/04/2024", "Bệnh viện Nhân dân 115", 400)
            )
        ),
        Donor(
            name = "Võ Thị Lan",
            age = 24,
            phone = "0901234567",
            bloodType = "A+",
            donationHistory = listOf(
                DonationRecord("03/03/2024", "Bệnh viện Đa khoa Tỉnh", 450)
            )
        ),

        // 5 người nhóm máu A-
        Donor(
            name = "Nguyễn Văn Lâm",
            age = 31,
            phone = "0911122233",
            bloodType = "A-",
            donationHistory = listOf(
                DonationRecord("12/01/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Trần Thị Mai",
            age = 23,
            phone = "0922233344",
            bloodType = "A-",
            donationHistory = listOf(
                DonationRecord("28/02/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Lê Văn Minh",
            age = 33,
            phone = "0933344455",
            bloodType = "A-",
            donationHistory = listOf(
                DonationRecord("15/03/2024", "Bệnh viện Nhân dân 115", 500)
            )
        ),
        Donor(
            name = "Phạm Thị Nga",
            age = 26,
            phone = "0944455566",
            bloodType = "A-",
            donationHistory = listOf(
                DonationRecord("09/04/2024", "Bệnh viện Chợ Rẫy", 400)
            )
        ),
        Donor(
            name = "Hoàng Văn Nam",
            age = 30,
            phone = "0955566677",
            bloodType = "A-",
            donationHistory = listOf(
                DonationRecord("22/05/2024", "Viện Huyết học", 450)
            )
        ),

        // 5 người nhóm máu B+
        Donor(
            name = "Đỗ Thị Ngọc",
            age = 28,
            phone = "0966677788",
            bloodType = "B+",
            donationHistory = listOf(
                DonationRecord("18/01/2024", "Bệnh viện Nhân dân 115", 500),
                DonationRecord("25/04/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Ngô Văn Phong",
            age = 34,
            phone = "0977788899",
            bloodType = "B+",
            donationHistory = listOf(
                DonationRecord("14/02/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Bùi Thị Phương",
            age = 25,
            phone = "0988899900",
            bloodType = "B+",
            donationHistory = listOf(
                DonationRecord("08/03/2024", "Bệnh viện Đại học Y Dược", 400)
            )
        ),
        Donor(
            name = "Đặng Văn Quang",
            age = 29,
            phone = "0999900011",
            bloodType = "B+",
            donationHistory = listOf(
                DonationRecord("30/03/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Võ Thị Quỳnh",
            age = 27,
            phone = "0900011122",
            bloodType = "B+",
            donationHistory = listOf(
                DonationRecord("12/05/2024", "Bệnh viện Nhân dân Gia Định", 500)
            )
        ),

        // 5 người nhóm máu B-
        Donor(
            name = "Nguyễn Văn Sơn",
            age = 32,
            phone = "0911223344",
            bloodType = "B-",
            donationHistory = listOf(
                DonationRecord("19/01/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Trần Thị Thu",
            age = 24,
            phone = "0922334455",
            bloodType = "B-",
            donationHistory = listOf(
                DonationRecord("26/02/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Lê Văn Tùng",
            age = 36,
            phone = "0933445566",
            bloodType = "B-",
            donationHistory = listOf(
                DonationRecord("13/03/2024", "Bệnh viện Nhân dân 115", 500)
            )
        ),
        Donor(
            name = "Phạm Thị Trang",
            age = 26,
            phone = "0944556677",
            bloodType = "B-",
            donationHistory = listOf(
                DonationRecord("07/04/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Hoàng Văn Toàn",
            age = 31,
            phone = "0955667788",
            bloodType = "B-",
            donationHistory = listOf(
                DonationRecord("21/05/2024", "Bệnh viện Chợ Rẫy", 400)
            )
        ),

        // 5 người nhóm máu AB+
        Donor(
            name = "Đỗ Thị Thanh",
            age = 28,
            phone = "0966778899",
            bloodType = "AB+",
            donationHistory = listOf(
                DonationRecord("16/01/2024", "Bệnh viện Nhân dân 115", 500)
            )
        ),
        Donor(
            name = "Ngô Văn Thịnh",
            age = 33,
            phone = "0977889900",
            bloodType = "AB+",
            donationHistory = listOf(
                DonationRecord("24/02/2024", "Viện Huyết học", 450),
                DonationRecord("18/05/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Bùi Thị Tâm",
            age = 25,
            phone = "0988990011",
            bloodType = "AB+",
            donationHistory = listOf(
                DonationRecord("11/03/2024", "Bệnh viện Đại học Y Dược", 400)
            )
        ),
        Donor(
            name = "Đặng Văn Việt",
            age = 30,
            phone = "0999001122",
            bloodType = "AB+",
            donationHistory = listOf(
                DonationRecord("29/04/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Võ Thị Vân",
            age = 27,
            phone = "0900112233",
            bloodType = "AB+",
            donationHistory = listOf(
                DonationRecord("14/05/2024", "Bệnh viện Nhân dân Gia Định", 500)
            )
        ),

        // 5 người nhóm máu AB-
        Donor(
            name = "Nguyễn Văn Hoàng",
            age = 34,
            phone = "0911334455",
            bloodType = "AB-",
            donationHistory = listOf(
                DonationRecord("17/01/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Trần Thị Huyền",
            age = 26,
            phone = "0922445566",
            bloodType = "AB-",
            donationHistory = listOf(
                DonationRecord("23/02/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Lê Văn Hải",
            age = 29,
            phone = "0933556677",
            bloodType = "AB-",
            donationHistory = listOf(
                DonationRecord("10/03/2024", "Bệnh viện Nhân dân 115", 500)
            )
        ),
        Donor(
            name = "Phạm Thị Yến",
            age = 31,
            phone = "0944667788",
            bloodType = "AB-",
            donationHistory = listOf(
                DonationRecord("06/04/2024", "Viện Huyết học", 450)
            )
        ),
        Donor(
            name = "Hoàng Văn Bình",
            age = 28,
            phone = "0955778899",
            bloodType = "AB-",
            donationHistory = listOf(
                DonationRecord("20/05/2024", "Bệnh viện Chợ Rẫy", 400)
            )
        ),

        // 5 người nhóm máu O+
        Donor(
            name = "Đỗ Thị Loan",
            age = 25,
            phone = "0966889900",
            bloodType = "O+",
            donationHistory = listOf(
                DonationRecord("13/01/2024", "Bệnh viện Nhân dân 115", 500)
            )
        ),
        Donor(
            name = "Ngô Văn Dũng",
            age = 32,
            phone = "0977990011",
            bloodType = "O+",
            donationHistory = listOf(
                DonationRecord("27/02/2024", "Viện Huyết học", 450),
                DonationRecord("16/05/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Bùi Thị Mỹ",
            age = 24,
            phone = "0988001122",
            bloodType = "O+",
            donationHistory = listOf(
                DonationRecord("09/03/2024", "Bệnh viện Đại học Y Dược", 400)
            )
        ),
        Donor(
            name = "Đặng Văn Thọ",
            age = 35,
            phone = "0999112233",
            bloodType = "O+",
            donationHistory = listOf(
                DonationRecord("04/04/2024", "Bệnh viện Chợ Rẫy", 350)
            )
        ),
        Donor(
            name = "Võ Thị Hồng",
            age = 27,
            phone = "0900223344",
            bloodType = "O+",
            donationHistory = listOf(
                DonationRecord("17/05/2024", "Bệnh viện Nhân dân Gia Định", 500)
            )
        )
    )
}
private fun getSampleBloodRequests(): List<BloodRequest> {
    return listOf(
        BloodRequest(1, "Nguyễn Minh Tuấn", "A+", "TP.HCM", 350, "Pending", "High", "0912345678", "Bệnh viện Chợ Rẫy", "Cần gấp cho phẫu thuật tim"),
        BloodRequest(2, "Trần Thị Thu Hà", "B-", "Hà Nội", 450, "Approved", "Medium", "0923456789", "Bệnh viện Bạch Mai", "Tai nạn giao thông, cần truyền máu"),
        BloodRequest(3, "Phạm Quốc Khánh", "O+", "Đà Nẵng", 500, "Pending", "Critical", "0934567890", "Bệnh viện Đà Nẵng", "Mổ cấp cứu, tình trạng nguy kịch"),
        BloodRequest(4, "Lê Thị Mai Lan", "AB+", "Cần Thơ", 250, "Rejected", "Low", "0945678901", "Bệnh viện Đa khoa Cần Thơ", "Chuẩn bị cho phẫu thuật định kỳ"),
        BloodRequest(5, "Hoàng Văn Phúc", "A-", "Huế", 400, "Pending", "High", "0956789012", "Bệnh viện Trung ương Huế", "Sản phụ băng huyết sau sinh"),
        BloodRequest(6, "Đỗ Thị Hồng Nhung", "B+", "Hải Phòng", 300, "Approved", "Medium", "0967890123", "Bệnh viện Hữu Nghị Việt Tiệp", "Bệnh nhân ung thư cần truyền máu"),
        BloodRequest(7, "Ngô Quang Huy", "O-", "Nghệ An", 350, "Pending", "Critical", "0978901234", "Bệnh viện Đa khoa Nghệ An", "Tai nạn lao động, mất nhiều máu"),
        BloodRequest(8, "Vũ Thị Bích Ngọc", "AB-", "Quảng Ninh", 450, "Approved", "High", "0989012345", "Bệnh viện Bãi Cháy", "Phẫu thuật thay khớp háng"),
        BloodRequest(9, "Đặng Minh Khoa", "A+", "TP.HCM", 500, "Pending", "Medium", "0990123456", "Bệnh viện Nhân dân 115", "Bệnh nhân sốt xuất huyết nặng"),
        BloodRequest(10, "Nguyễn Thị Hồng Anh", "B-", "Hà Nội", 250, "Rejected", "Low", "0901234567", "Bệnh viện Việt Đức", "Chuẩn bị phẫu thuật chỉnh hình"),
        BloodRequest(11, "Trương Công Đạt", "O+", "Đà Nẵng", 300, "Approved", "High", "0911122233", "Bệnh viện Giao thông Vận tải", "Tai nạn giao thông đa chấn thương"),
        BloodRequest(12, "Phan Thị Thanh Hằng", "AB+", "Cần Thơ", 400, "Pending", "Medium", "0922233344", "Bệnh viện Phụ sản Cần Thơ", "Sản phụ thiếu máu khi mang thai"),
        BloodRequest(13, "Lưu Văn Hải", "A-", "Huế", 350, "Approved", "Critical", "0933344455", "Bệnh viện Trường Đại học Y Dược", "Ngộ độc cấp, suy tủy"),
        BloodRequest(14, "Nguyễn Thị Kim Chi", "B+", "Hải Phòng", 450, "Pending", "High", "0944455566", "Bệnh viện Trẻ em Hải Phòng", "Trẻ em bị bệnh bạch cầu"),
        BloodRequest(15, "Trần Hoàng Long", "O-", "Nghệ An", 500, "Rejected", "Medium", "0955566677", "Bệnh viện Đa khoa Tỉnh Nghệ An", "Phẫu thuật thẩm mỹ"),
        BloodRequest(16, "Lê Ngọc Bảo Trân", "AB-", "Quảng Ninh", 250, "Approved", "Low", "0966677788", "Bệnh viện Đa khoa Quảng Ninh", "Chuẩn bị sinh mổ"),
        BloodRequest(17, "Phạm Minh Quân", "A+", "TP.HCM", 400, "Pending", "High", "0977788899", "Bệnh viện Nhi Đồng 1", "Trẻ em phẫu thuật tim bẩm sinh"),
        BloodRequest(18, "Nguyễn Thị Thu Trang", "B-", "Hà Nội", 300, "Approved", "Critical", "0988899900", "Bệnh viện K", "Bệnh nhân ung thư giai đoạn cuối"),
        BloodRequest(19, "Võ Văn Hùng", "O+", "Đà Nẵng", 350, "Pending", "Medium", "0999900011", "Bệnh viện Hoàn Mỹ", "Phẫu thuật nội soi túi mật"),
        BloodRequest(20, "Đào Thị Thanh Tâm", "AB+", "Cần Thơ", 450, "Approved", "High", "0900011122", "Bệnh viện Đa khoa Quốc tế", "Ghép thận cấp cứu")
    )
}