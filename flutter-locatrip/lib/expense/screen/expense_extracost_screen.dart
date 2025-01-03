import 'package:flutter/material.dart';
import 'package:flutter_locatrip/expense/model/expense_model.dart';

class ExpenseExtracostScreen extends StatefulWidget {
  final int tripId;
  final String selectedDate; // 날짜 선택을 위해

  const ExpenseExtracostScreen({
    super.key,
    required this.tripId,
    required this.selectedDate,
  });

  @override
  State<ExpenseExtracostScreen> createState() => _ExpenseExtracostScreenState();
}

class _ExpenseExtracostScreenState extends State<ExpenseExtracostScreen> {
  final ExpenseModel expenseModel = ExpenseModel();

  final TextEditingController _categoryController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();
  final TextEditingController _amountController = TextEditingController();

  String _selectedPaymentMethod = '현금';

  Future<void> _saveExpense() async {
    try {
      await expenseModel.createExpense({
        'tripId': widget.tripId,
        'date': widget.selectedDate,
        'category': _categoryController.text,
        'description': _descriptionController.text,
        'amount': double.parse(_amountController.text),
      });

      Navigator.pop(context);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('비용이 성공적으로 추가되었습니다.')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('오류 발생: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('비용 추가')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: _categoryController,
              decoration: const InputDecoration(labelText: '카테고리'),
            ),
            TextField(
              controller: _descriptionController,
              decoration: const InputDecoration(labelText: '설명'),
            ),
            TextField(
              controller: _amountController,
              decoration: const InputDecoration(labelText: '금액'),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 20),
            DropdownButtonFormField(
              value: _selectedPaymentMethod,
              items: ['현금', '카드'].map((method) {
                return DropdownMenuItem(
                  value: method,
                  child: Text(method),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedPaymentMethod = value.toString();
                });
              },
              decoration: const InputDecoration(labelText: '결제 수단'),
            ),
            const SizedBox(height: 20),
            Center(
              child: ElevatedButton(
                onPressed: _saveExpense,
                child: const Text('완료'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
