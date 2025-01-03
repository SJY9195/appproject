import 'package:flutter/material.dart';
import 'package:flutter_locatrip/common/widget/color.dart';
import 'package:flutter_locatrip/expense/model/expense_model.dart';
import 'package:flutter_locatrip/expense/screen/expense_extracost_screen.dart';

class ExpenseScreen extends StatefulWidget {
  final int tripId;

  const ExpenseScreen({super.key, required this.tripId});

  @override
  State<ExpenseScreen> createState() => _ExpenseScreenState();
}

class _ExpenseScreenState extends State<ExpenseScreen> {
  final ExpenseModel expenseModel = ExpenseModel();
  Map<String, dynamic> groupedExpenses = {};
  bool isLoading = true;

  String selectedPeriod = '기간전체';

  final Map<String, IconData> categoryIcons = {
    '숙소': Icons.night_shelter,
    '식비': Icons.restaurant,
    '교통': Icons.directions_car,
    '관광': Icons.confirmation_number,
    '쇼핑': Icons.shopping_bag,
    '기타': Icons.sms,
  };

  @override
  void initState() {
    super.initState();
    loadExpensesGroupedByDays();
  }

  Future<void> loadExpensesGroupedByDays() async {
    try {
      final data = await expenseModel.getExpensesGroupedByDays(widget.tripId);
      setState(() {
        groupedExpenses = data;
        isLoading = false;
      });
    } catch (e) {
      print('Error: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  void _showPeriodPicker() {
    showModalBottomSheet(
      context: context,
      builder: (context) {
        return SafeArea(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const ListTile(
                title: Text('날짜선택', style: TextStyle(color: grayColor, fontSize: 15)),
              ),

              // '기간전체' 필터 옵션
              ListTile(
                title: Text(
                  '기간전체',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    color: selectedPeriod == '기간전체' ? pointBlueColor : blackColor,
                  ),
                ),
                trailing: selectedPeriod == '기간전체'
                    ? const Icon(Icons.check, color: pointBlueColor)
                    : null,
                onTap: () {
                  setState(() {
                    selectedPeriod = '기간전체';
                  });
                  Navigator.pop(context);
                },
              ),


              // 날짜별 필터 옵션
              ...groupedExpenses.entries.map((entry) {
                final day = entry.key;
                final date = entry.value['date'];

                final formattedDate = day == "preparation"
                    ? '여행 준비'
                    : '$day ($date)';

                return ListTile(
                  title: Text(
                    formattedDate,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: selectedPeriod == day ? pointBlueColor : blackColor,
                    ),
                  ),
                  trailing: selectedPeriod == day
                      ? const Icon(Icons.check, color: pointBlueColor)
                      : null,
                  onTap: () {
                    setState(() {
                      selectedPeriod = day;
                    });
                    Navigator.pop(context);
                  },
                );
              }).toList(),
            ],
          ),
        );
      },
    );
  }


  void _navigateToAddExpense(String selectedDate) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => ExpenseExtracostScreen(
          tripId: widget.tripId,
          selectedDate: selectedDate,
        ),
      ),
    ).then((_) => loadExpensesGroupedByDays());
  }

  @override
  Widget build(BuildContext context) {
    final filteredExpenses = selectedPeriod == '기간전체'
        ? groupedExpenses
        : {
      selectedPeriod: groupedExpenses[selectedPeriod],
    };


    return Scaffold(
      appBar: PreferredSize(
        preferredSize: const Size.fromHeight(110),
        child: AppBar(
          automaticallyImplyLeading: false, // 기본 여백 제거
          title: const Text('가계부'),
          flexibleSpace: Column(
            mainAxisAlignment: MainAxisAlignment.end,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: GestureDetector(
                  onTap: _showPeriodPicker,
                  child: Container(
                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                    decoration: BoxDecoration(
                      border: Border.all(color: grayColor),
                    ),
                    child: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        Text(
                          selectedPeriod == 'preparation' ? '여행 준비' : '$selectedPeriod ${groupedExpenses[selectedPeriod]?['date'] ?? ''}',
                          style: const TextStyle(fontSize: 14, color: blackColor),
                        ),
                        const Icon(Icons.keyboard_arrow_down, color: grayColor),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : ListView(
        children: filteredExpenses.entries.map((entry) {
          final day = entry.key;
          final dayData = entry.value as Map<String, dynamic>;
          final date = dayData['date'];

          return ExpansionTile(
            title: Text(
              day == "preparation" ? '여행 준비' : '$day $date',
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            children: [
              ...(dayData['expenses'] as List).map((expense) {
                final String category = expense['category'] ?? '기타';
                final IconData icon = categoryIcons[category] ?? Icons.sms;

                return ListTile(
                  leading: Icon(icon, color: pointBlueColor),
                  title: Text(expense['description']),
                  trailing: Text('₩${expense['amount']}'),
                );
              }).toList(),
              OutlinedButton(
                style: OutlinedButton.styleFrom(
                  foregroundColor: blackColor,
                  side: const BorderSide(color: grayColor),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(5),
                  ),
                  padding: const EdgeInsets.symmetric(horizontal: 420, vertical: 10),
                  textStyle: const TextStyle(fontSize: 16),
                ),
                onPressed: () => _navigateToAddExpense(date),
                child: const Text('비용 추가'),
              ),
            ],
          );
        }).toList(),
      ),
    );
  }
}
